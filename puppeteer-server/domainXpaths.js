const domainXpaths = {
    coupang: {
        thumbnails: '//*[@id="repImageContainer"]/img',
        name: '//*[@id="contents"]/div[1]/div/div[3]/div[3]/h2',
        price: '//*[@id="contents"]/div[1]/div/div[3]/div[5]/div[1]/div/div[2]/span[1]/strong/text()',
        delivery: '//*[@id="contents"]/div[1]/div/div[3]/div[7]/div[2]/div/div[1]/div[1]/span'
    },
    '11st': {
        thumbnails: '//*[@id="thumb"]/div/span[1]/img',
        name: '//*[@id="productInfoMain"]/div[2]/div/h2',
        price: '//*[@id="prdcInfoColumn2"]/div[1]/div[1]/span',
        delivery: '//*[@id="prdcInfoColumn2"]/div[3]/div[1]/div[1]'
    }
}

module.exports = domainXpaths;