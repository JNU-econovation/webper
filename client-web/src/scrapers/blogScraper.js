import puppeteer from '../apis/puppeteer';

const blogScraper = async (url, callback) => {
    try {
        const response = await puppeteer.get('blog', {
            params: {
                input_url: url
            }
        })

        return callback(null, { ...response.data });
    } catch (err) {
        alert("자동으로 scrap해올 수 없는 페이지입니다. 직접 컴포넌트를 만들어주세요!");
        return callback(null, { title: "edit버튼을 눌러 직접 작성해주세요", thumbnails: window.origin + "/images/emptyImage.png", redirectionLink: url });
    }
}

export default blogScraper;