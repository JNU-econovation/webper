const express = require('express');

const getDomainWishInfo = require('./getDomainWishInfo');
const getGeneralWishInfo = require('./getGeneralWishInfo');

const app = express();

app.get('/wish', async (req, res) => {
    console.log('/wish요청 처리중');
    const domain = req.query.shoppingmall;
    const data = await getWishInfo(req.query.input_url, domain);

    // response to webper client

    if (!data) res.status(500).send({ status: 500, message: 'internal error', type: 'internal' });
    else {
        console.log("index.js", data)
        res.writeHead(200, {
            'Content-Type': 'application/json',
            'Access-Control-Allow-Origin': '*',
            "Access-Control-Allow-Credentials": "true"
        });
        res.write(JSON.stringify(data));
        res.end();
    }
})

const getWishInfo = async (url, domain) => {
    let data;

    await getDomainWishInfo(url, domain, (err, response) => {
        if (err) {
            console.log(err.name, err.message);
        } else data = response;
    });

    if (!data) {
        await getGeneralWishInfo(url, domain, (err, response) => {
            if (err) console.log(err.name, err.message);
            else data = response;
        });
    }

    return data;
}

app.listen(3002);
