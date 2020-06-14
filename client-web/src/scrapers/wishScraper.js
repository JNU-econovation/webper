import puppeteer from '../apis/puppeteer';

const wishScraper = async (url, callback) => {
    const response = await puppeteer.get('/wish/coupang', {
        params: {
            input_url: url
        }
    })

    console.log(response);
    // const wish_detail = { srcTxt, price, name, delivery };
    // return callback(null, wish_detail);
}

export default wishScraper;