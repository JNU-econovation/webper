import React from 'react';
import youtube from '../api/youtube';

class Video extends React.Component {
    state = { url: "https://www.youtube.com/watch?v=k8gx-C7GCGU", video_id: null, video: null };

    componentDidMount() {
        this.setState({ video_id: this.findVideoId() }, () => this.fetchVideo())
    }

    findVideoId() {
        let video_id = this.state.url.split('v=')[1];
        let ampersandPosition = video_id.indexOf('&');
        if (ampersandPosition !== -1) {
            video_id = video_id.substring(0, ampersandPosition);
        }

        return video_id;
    }

    fetchVideo = async () => {
        const video_id = this.state.video_id;
        const response = await youtube.get('/videos', {
            params: {
                id: video_id
            }
        })

        this.setState({ video: response.data.items[0].snippet })
    }

    renderVideoIframe() {
        const videoSrc = `https://www.youtube.com/embed/${this.state.video_id}`;

        return (
            <div>
                <iframe title="video player" src={videoSrc} controls="1" frameBorder="0" allowFullScreen />
            </div>
        )
    }

    renderDetail = () => {
        return (
            <div>
                {/* <a href="https://www.youtube.com/watch?v=k8gx-C7GCGU" target="_blank" rel="noopener noreferrer">
                    <img alt={this.state.video.title} className="ui image" src={this.state.video.thumbnails.medium.url} />
                </a> */}
                {this.renderVideoIframe()}
                <div>
                    <div>{this.state.video.title}</div>
                </div>
            </div>
        )
    }

    render() {
        if (this.state.video === null)
            return null;

        return (
            <div>
                {this.renderDetail()}
            </div>
        )
    }
}

export default Video;

