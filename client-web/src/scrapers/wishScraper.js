import puppeteer from '../apis/puppeteer';

const wishScraper = async (url, callback) => {
    const wish_domain = domainToString(url);
    const response = await puppeteer.get('wish', {
        params: {
            shoppingmall: wish_domain,
            input_url: url
        }
    })

    const wish_detail = response.data;
    return callback(null, { ...wish_detail, description: "" });
}

const domainToString = url => {
    if (url.indexOf("www.coupang.com") !== -1) return 'coupang';
    return 'default';
}

export default wishScraper;