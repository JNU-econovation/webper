export default (state = {}, action) => {
    switch (action.type) {
        case 'CREATE_SCRAP':
            return { ...state, [action.payload.id]: action.payload };
        default:
            return state;
    }
}