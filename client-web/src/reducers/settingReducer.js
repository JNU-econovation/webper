const INITIAL_STATE = {
    webperTitle: "webper",
    mainDirs: {
        first: '1',
        second: '2',
        third: '3',
        fourth: '4',
        fifth: '5'
    }
}

export default (state = INITIAL_STATE, action) => {
    switch (action.type) {
        case 'SETTING':
            return { ...state, ...action.payload }
        default:
            return state;
    }
}