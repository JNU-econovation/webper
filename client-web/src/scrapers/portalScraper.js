import puppeteer from '../apis/puppeteer';

const portalScraper = async (url, callback) => {
    try {
        const response = await puppeteer.get('portal', {
            params: {
                input_url: url
            }
        })

        return callback(null, { ...response.data, description: "" });
    } catch (err) {
        alert("자동으로 scrap해올 수 없는 페이지입니다. 직접 컴포넌트를 만들어주세요!");
        return callback(null, { name: "edit버튼을 눌러 직접 작성해주세요", favicon: window.origin + "/images/emptyImage.png", redirectionLink: url });
    }
}

export default portalScraper;