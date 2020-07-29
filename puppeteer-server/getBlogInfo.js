const cheerio = require('cheerio');
const axios = require('axios');
const iconv = require('iconv-lite');
const resultFormat = require('./resultFormat');

const getBlogInfo = async (url, callback) => {
console.log("in getBlogInfo", url);
	let result = resultFormat.blogFormat;
    await getHtml(url, (err, html) => {
        if (err) {
            console.log("getHtml 오류");
            let error = new Error(err.message);
            error.name = 'getHtmlError'
            return callback(error, null);
        } else {
            const $ = cheerio.load(decode(html));
            result.title = getTitle($);
            result.thumbnailURL = getThumbnails($, url);
            result.description = getDescription($)
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

const getTitle = $ => {
    let title; const titlelist = [];
    titlelist[0] = $('meta[property="og:title"]').attr('content');
    titlelist[1] = $('meta[name="twitter:title"]').attr('content');
    titlelist[2] = $('title').text();

    titlelist.forEach(list => {
        if (!title && list)
            title = list;
    });

    return title;
}

const getThumbnails = ($, url) => {
    let thumbnails; const thumbnailslist = [];
    thumbnailslist[0] = $('meta[property="og:image"]').attr('content');
    thumbnailslist[1] = $('meta[name="twitter:image"]').attr('content');
    thumbnailslist[2] = $('link[rel="apple-touch-icon"]').attr('href');
    thumbnailslist[3] = $('link[rel="icon"]').attr('href');

    thumbnailslist.forEach(list => {
        if (!thumbnails && list) {
            if (list.substring(-3) !== 'gif') list = list.replace((/(gif$)/), "jpg");
            if ((list.substring(0, 4) !== 'http') && !(list.substring(0, 2) === '//')) {
                const protocol = url.split("://")[0];
                let root = url.split("://")[1].split('/')[0];
                if (list.substring(0, 1) !== '/') root = root + '/';
                console.log(list.substring(0, 1))
                console.log(list.substring(0, 1) !== '/')
                thumbnails = protocol + "://" + root + list;
            } else thumbnails = list;
        }
    })
    return thumbnails;
}

const getDescription = $ => {
    let description; const descriptionlist = [];
    descriptionlist[0] = $('meta[property="og:description"]').attr('content');
    descriptionlist[1] = $('meta[name="twitter:description"]').attr('content');
    descriptionlist[2] = $('meta[name="description"]').attr('content');

    descriptionlist.forEach(list => {
        if (!description && list)
            description = list;
    });

    if (descriptionlist.length > 70) descriptionlist = descriptionlist.substring(0, 70) + "...";
    return description;
}

const trim = str => {
    return str.replace(/(\s*)/g, "");
}

module.exports = getBlogInfo;
