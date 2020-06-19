const cheerio = require('cheerio');
const axios = require('axios');
const iconv = require('iconv-lite');
const resultFormat = require('./resultFormat');

const getPortalInfo = async (url, callback) => {
    let result = resultFormat.portalFormat;
    await getHtml(url, (err, html) => {
        if (err) {
            console.log("getHtml 오류");
            let error = new Error(err.message);
            error.name = 'getHtmlError'
            return callback(error, null);
        } else {
            const $ = cheerio.load(decode(html));
            result.name = getName($);
            result.favicon = getFavicon($, url);
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
    charsetlist[0] = $('meta[charset="utf-8"]').attr('charset');
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
    let name; const namelist = [];
    namelist[0] = $('meta[property="og:title"]').attr('content');
    namelist[1] = $('title').text();

    namelist.forEach(list => {
        if (!name && list)
            name = list;
    });

    return name;
}

const getFavicon = ($, url) => {
    let favicon; const faviconlist = [];
    faviconlist[0] = $('link[rel="shortcut icon"]').attr('href');
    faviconlist[1] = $('link[rel="fluid-icon"]').attr('href');
    faviconlist[2] = $('link[rel="apple-touch-icon"]').attr('href');
    faviconlist[3] = $('link[rel="icon"]').attr('href');

    faviconlist.forEach(list => {
        console.log(list);
        if (!favicon && list) {
            if ((list.substring(0, 1) === '/') && !(list.substring(0, 2) === '//')) {
                const protocol = url.split("://")[0];
                const root = url.split("://")[1].split('/')[0];
                favicon = protocol + "://" + root + list;
            } else favicon = list;
        }
    })
    console.log('favicon', favicon);
    return favicon;
}
const trim = str => {
    return str.replace(/(\s*)/g, "");
}

module.exports = getPortalInfo;