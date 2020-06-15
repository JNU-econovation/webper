const puppeteer = require('puppeteer');
const express = require('express');

const app = express();


app.get('/wish/coupang', async (req, res) => {

    const response = await getInfo(req.query.input_url);
    res.writeHead(200, {
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': '*'
    });
    res.write(JSON.stringify(response));
    res.end();
})

const getInfo = async url => {

    const browser = await puppeteer.launch({
        args: ['--user-agent=<webper>']
    });
    const page = await browser.newPage();
    await page.goto(url);

    const [el] = await page.$x('//*[@id="repImageContainer"]/img');
    const src = await el.getProperty('src');
    const srcTxt = await src.jsonValue();

    const [el2] = await page.$x('//*[@id="contents"]/div[1]/div/div[3]/div[3]/h2');
    const txt = await el2.getProperty('textContent');
    const name = await txt.jsonValue();

    const [el3] = await page.$x('//*[@id="contents"]/div[1]/div/div[3]/div[5]/div[1]/div/div[2]/span[1]/strong/text()');
    const txt2 = await el3.getProperty('textContent');
    const price = await txt2.jsonValue();

    const [el4] = await page.$x('//*[@id="contents"]/div[1]/div/div[3]/div[7]/div[2]/div/div[1]/div[1]/span');
    const txt3 = await el4.getProperty('textContent');
    const delivery = await txt3.jsonValue();

    const result = { name, thumbnails, price, delivery, redirectionLink: url }
    console.log(result);

    await browser.close();

    return result
}

app.listen(3002);
