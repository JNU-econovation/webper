import React from 'react';

import VideoScrap from '../scraps/VideoScrap';
import WishScrap from '../scraps/WishScrap';
import BlogScrap from '../scraps/BlogScrap';
import PortalScrap from '../scraps/PortalScrap';

const renderScraps = (directory, scraps) => {
    let scrap_component;
    if (directory)
        switch (directory.category) {
            case "VIDEO":
                scrap_component = scraps.map(scrap => {
                    return <VideoScrap video={scrap} key={scrap.id} />
                });
                break;
            case "WISHLIST":
                scrap_component = scraps.map(scrap => {
                    return <WishScrap wish={scrap} key={scrap.id} />
                });
                break;
            case "BLOG":
                scrap_component = scraps.map(scrap => {
                    return <BlogScrap blog={scrap} key={scrap.id} />
                });
                break;
            case "PORTAL":
                scrap_component = scraps.map(scrap => {
                    return <PortalScrap portal={scrap} key={scrap.id} />
                });
                break;

            default: return scrap_component;
        }
    return scrap_component;
}

export default renderScraps;
