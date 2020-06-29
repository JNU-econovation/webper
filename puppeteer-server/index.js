const puppeteer = require('puppeteer');
const express = require('express');
const domainXpaths = require('./domainXpaths');

const app = express();


app.get('/wish', async (req, res) => {
    const domain = req.query.shoppingmall;
    const response = await getInfo(req.query.input_url, domain);
    res.writeHead(200, {
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': '*'
    });
    res.write(JSON.stringify(response));
    res.end();
})

const getInfo = async (url, domain) => {

    const browser = await puppeteer.launch({
        args: ['--user-agent=<webper>']
    });
    const page = await browser.newPage();
    await page.goto(url);

    const [el] = await page.$x(domainXpaths.domain.thumbnails);
    const src = await el.getProperty('src');
    const thumbnails = await src.jsonValue();

    const [el2] = await page.$x(domainXpaths.domain.name);
    const txt = await el2.getProperty('textContent');
    const name = await txt.jsonValue();

    const [el3] = await page.$x(domainXpaths.domain.price);
    const txt2 = await el3.getProperty('textContent');
    const price = await txt2.jsonValue();

    const [el4] = await page.$x(domainXpaths.domain.delivery);
    const txt3 = await el4.getProperty('textContent');
    const delivery = await txt3.jsonValue();

    const result = { name, thumbnails, price, shoppingmall: domain, delivery, redirectionLink: url }
    console.log(result);

    await browser.close();

    return result
}

app.listen(3002);