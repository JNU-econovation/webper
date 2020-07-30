const puppeteer = require('puppeteer');
const domainXpaths = require('./domainXpaths');
const resultFormat = require('./resultFormat');

const getDomainWishInfo = async (url, domain, callback) => {
    let result = resultFormat.wishFormat;

    try {
        const browser = await puppeteer.launch({
            args: ['--user-agent=<webper>']
        });
        const page = await browser.newPage();
        await page.goto(url);

        const [el] = await page.$x(domainXpaths[domain].name);
        const txt = await el.getProperty('textContent');
       	if (txt)
	    result.title = await txt.jsonValue();

        const [el2] = await page.$x(domainXpaths[domain].thumbnails);
        const src = await el2.getProperty('src');
        if (src)
	    result.thumbnailURL = await src.jsonValue();

        const [el3] = await page.$x(domainXpaths[domain].price);
        const txt2 = await el3.getProperty('textContent');
        if (txt2)
	    result.price = trim(await txt2.jsonValue());

        const [el4] = await page.$x(domainXpaths[domain].delivery);
        const txt3 = await el4.getProperty('textContent');
        if (txt3)
	    result.deliveryInfo = trim(await txt3.jsonValue());

        result.shoppingMall = domain;
	    console.log("shoppingMall:",result.shoppingMall, "domain:", domain);
        result.redirectionLink = url;

        console.log(result);
        await browser.close();
        return callback(null, result);
    } catch (error) {
        console.log("cors문제로 puppeteer접근 불가")
        return callback(error, null);
    }
}

const trim = str => {
    return str.replace(/(\s*)/g, "");
}

module.exports = getDomainWishInfo;
