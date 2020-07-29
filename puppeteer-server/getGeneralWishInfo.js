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
            const $ = cheerio.load(decode(html));
            result.title = getName($);
            result.thumbnailURL = getThumbnails($);
            result.price = getPrice($);
            result.shoppingMall = url.split("://")[1].split('/')[0];
            result.redirectionLink = url;

            console.log(result);
            return callback(null, result);
        }
    })
}

const getHtml = async (url, callback) => {
    try {
        const { data } = await axios({
            url,
            method: 'GET',
            responseType: 'arraybuffer'
        });
        return callback(null, data);
    } catch (err) {
        console.log(err.name, err.message);
        return callback(err, null);
    }
}

const decode = html => {
    const $ = cheerio.load(html); let charsetlist = [];
    let charset;
    charsetlist[0] = $('meta[charset]').attr('charset');
    charsetlist[1] = $('meta[http-equiv="Content-Type"]').attr('content');

    if (charsetlist[1]) {
        let contentlist = charsetlist[1].split(";");
        contentlist.forEach(list => {
            list = trim(list);
            if (list.indexOf('charset') !== -1) charsetlist[1] = list.split('charset=')[1];
        })
    }

    charsetlist.forEach(list => {
        if (list) {
            charset = list;
        }
    })

    if (!charset) charset = 'utf-8';
    return iconv.decode(html, charset).toString();
}

const getName = $ => {
    let name = ""; const namelist = [];
    namelist[0] = $('meta[property="og:title"]').attr('content');;
    namelist[1] = $('title').text();

    namelist.forEach(list => {
        if (!name && list)
            name = list;
    });

    return name;
}

const getThumbnails = $ => {
    let thumbnails = ""; const thumblist = [];
    thumblist[0] = $('meta[property="og:image"]').attr('content');

    thumblist.forEach(list => {
        if (list) thumbnails = list;
    });

    return thumbnails;
}

const getPrice = $ => {
    let price = ""; const pricelist = [];
    pricelist[0] = $('meta[property="product:price:amount"]').attr('content');

    pricelist.forEach(list => {
        if (list) price = list;
    });

    return price;
}

const trim = str => {
    return str.replace(/(\s*)/g, "");
}

module.exports = getGeneralWishInfo;
