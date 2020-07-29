const express = require('express');

const getDomainWishInfo = require('./getDomainWishInfo');
const getGeneralWishInfo = require('./getGeneralWishInfo');
const getPortalInfo = require('./getPortalInfo');
const getBlogInfo = require('./getBlogInfo');

const app = express();

app.get('/wish', async (req, res) => {
    console.log('/wish요청 처리중');
    const domain = req.query.shoppingMall;
    let data;
    // if domain is not registered in this application => generalInfo
    if (domain === 'default') {
        console.log("도메인 default");
        await getGeneralWishInfo(req.query.input_url, domain, (err, response) => {
            if (err) console.log(err.message);
            else data = response;
        })
    }

    // if domain is registered
    else data = await getWishInfo(req.query.input_url, domain);

    // response to webper client

    if (!data) {
        res.status(500).send({ status: 500, message: 'internal error', type: 'internal' });
        console.log("scrap 실패 에러 보냄");
    } else {
        res.writeHead(200, {
            'Content-Type': 'application/json',
            'Access-Control-Allow-Origin': '*'
        });
        res.write(JSON.stringify(data));
        res.end();
    }
})

app.get('/blog', async (req, res) => {
    let data;
    await getBlogInfo(req.query.input_url, (err, response) => {
        if (err) console.log(err.message);
        else data = response;
    })

    if (!data) {
        res.status(500).send({ status: 500, message: 'internal error', type: 'internal' });
        console.log("scrap 실패 에러 보냄");
    } else {
        res.writeHead(200, {
            'Content-Type': 'application/json',
            'Access-Control-Allow-Origin': '*'
        });
        res.write(JSON.stringify(data));
        res.end();
    }
})

app.get('/portal', async (req, res) => {
    let data;
    await getPortalInfo(req.query.input_url, (err, response) => {
        if (err) console.log(err.message);
        else data = response;
    })

    if (!data) {
        res.status(500).send({ status: 500, message: 'internal error', type: 'internal' });
        console.log("scrap 실패 에러 보냄");
    } else {
        res.writeHead(200, {
            'Content-Type': 'application/json',
            'Access-Control-Allow-Origin': '*'
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
