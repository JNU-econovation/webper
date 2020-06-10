import _ from 'lodash';

export default (state = {}, action) => {
    switch (action.type) {
        case 'CREATE_SCRAP':
            return { ...state, [action.payload.id]: action.payload };
        case 'FETCH_SCRAPS':
            // return { ...state, ..._.mapKeys(action.payload, 'id') };
            return { ..._.mapKeys(action.payload, 'id') } // directory detail의 id가 바뀌었을 때 해당하는 parentid의 scrap컴포넌트만 state로 관리하도록 ...state를 뺐음.
        // 문제 생기면 수정하고 다른 방법 찾아볼것
        default:
            return state;
    }
}