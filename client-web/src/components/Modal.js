import React from 'react';
import ReactDOM from 'react-dom';

const Modal = props => {
    return ReactDOM.createPortal(
        <div onClick={props.onDismiss} className="modal-background">
            <div onClick={(e) => e.stopPropagation()} className="modal-content">
                <div>{props.title}</div>
                <div>{props.content}
                </div>
            </div>
        </div>,
        document.querySelector('#modal')
    );
};

export default Modal;