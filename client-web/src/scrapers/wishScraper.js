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
    console.log(response);
    return callback(null, { ...wish_detail, description: "" });
}

const domainToString = url => {
    if (url.indexOf("www.coupang.com") !== -1) return 'coupang';
    if (url.indexOf("www.11st.co.kr") !== -1) return '11st';
    if (url.indexOf("deal.11st.co.kr") !== -1) return 'deal11st';
    return 'default';
}

export default wishScraper;