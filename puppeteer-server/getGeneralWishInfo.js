const cheerio = require('cheerio');
const axios = require('axios');
const iconv = require('iconv-lite');
const resultFormat = require('./resultFormat');

const getGeneralWishInfo = async (url, domain, callback) => {
    let result = resultFormat.wishFormat;
    await getHtml(url, (err, html) => {
        if (err) {
            console.log("getHtml 오류");
            let error = new Error(err.message);
            error.name = 'getHtmlError'
            return callback(error, null);
        } else {
            const $ = cheerio.load(html);

            result.name = getName($);
            result.thumbnails = getThumbnails($);
            result.price = getPrice($);
            result.shoppingmall = domain;
            result.redirectionLink = url;

            return callback(null, result);
        }
    })
}

const getHtml = async (url, callback) => {
    let contents;
    try {
        const { data } = await axios({
            url,
            method: 'GET',
            responseType: 'arraybuffer'
        });
        contents = iconv.decode(data, "EUC-KR").toString();
        return callback(null, contents);
    } catch (err) {
        console.log(err.name, err.message);
        return callback(err, null);
    }
}

const getName = $ => {
    let name; const namelist = [];
    namelist[0] = $('title').text();
    namelist[1] = $('meta[property="og:title"]').attr('content');

    namelist.forEach(list => {
        if (list) name = list;
    });

    return name;
}

const getThumbnails = $ => {
    let thumbnails; const thumblist = [];
    thumblist[0] = $('meta[property="og:image"]').attr('content');

    thumblist.forEach(list => {
        if (list) thumbnails = list;
    });

    return thumbnails;
}

const getPrice = $ => {
    let price; const pricelist = [];
    pricelist[0] = $('meta[property="product:price:amount"]').attr('content');

    pricelist.forEach(list => {
        if (list) price = list;
    });

    return price;
}

module.exports = getGeneralWishInfo;