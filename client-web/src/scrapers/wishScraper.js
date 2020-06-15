import puppeteer from '../apis/puppeteer';

const wishScraper = async (url, callback) => {
    const wish_domain = domainToString(url);
    const response = await puppeteer.get(`/wish/${wish_domain}`, {
        params: {
            input_url: url
        }
    })

    const wish_detail = response;
    return callback(null, wish_detail.data);
}

const domainToString = url => {
    if (url.indexOf("www.coupang.com") !== -1) return 'coupang';
    return 'default';
}

export default wishScraper;