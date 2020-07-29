import puppeteer from '../apis/puppeteer';

const wishScraper = async (url, callback) => {
    const wish_domain = domainToString(url);
    try {
        const response = await puppeteer.get('wish', {
            params: {
                shoppingMall: wish_domain,
                input_url: url
            }
        })
        const wish_detail = response.data;
        console.log(response);
        if (!wish_detail.thumbnailURL || wish_detail.thumbnailURL.substring(0, 1) == "/") wish_detail.thumbnailURL = window.origin + "/images/emptyImage.png"
        return callback(null, { ...wish_detail, description: "" });
    } catch (err) {
        alert("자동으로 scrap해올 수 없는 페이지입니다. 직접 컴포넌트를 만들어주세요!");
        return callback(null, { name: "edit버튼을 눌러 직접 작성해주세요", thumbnailURL: window.origin + "/images/emptyImage.png", redirectionLink: url });
    }
}

const domainToString = url => {
    if (url.indexOf("www.coupang.com") !== -1) return 'coupang';
    if (url.indexOf("www.11st.co.kr") !== -1) return '11st';
    if (url.indexOf("deal.11st.co.kr") !== -1) return 'deal11st';
    return 'default';
}

export default wishScraper;
