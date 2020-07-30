import React from 'react';

import VideoScrap from '../scraps/VideoScrap';
import WishScrap from '../scraps/WishScrap';
import BlogScrap from '../scraps/BlogScrap';
import PortalScrap from '../scraps/PortalScrap';

class RenderScraps extends React.Component {
	onDeleteScrap = (id) => {
		this.props.onDelete(id, this.props.directory.id, this.props.directory.category);
	}

    render() {
    let scrap_component;
	    
	    if (this.props.directory) {
		switch (this.props.directory.category) {
		    case "VIDEO":
			scrap_component = this.props.scraps.map(scrap => {
			    return <VideoScrap video={scrap} key={scrap.id} onDelete={this.onDeleteScrap} main={this.props.main} />
			});
			break;
		    case "WISHLIST":
			scrap_component = this.props.scraps.map(scrap => {
			    return <WishScrap wish={scrap} key={scrap.id} onDelete={this.onDeleteScrap} main={this.props.main} />
			});
			break;
		    case "BLOG":
			scrap_component = this.props.scraps.map(scrap => {
			    return <BlogScrap blog={scrap} key={scrap.id} onDelete={this.onDeleteScrap} main={this.props.main} />
			});
			break;
		    case "PORTAL":
			scrap_component = this.props.scraps.map(scrap => {
			    return <PortalScrap portal={scrap} key={scrap.id} onDelete={this.onDeleteScrap} main={this.props.main} />
			});
			break;

		    default: return scrap_component;
		}
	    return scrap_component;
	    }
	    else
		    return <div></div>
    }
}

export default RenderScraps;
