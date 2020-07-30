export default (state = {}, action) => {
	switch (action.type) {
		case 'FETCH_RANDOM_DIR':
			return {["dir"]:action.payload}
		default:
			return state;
	}
}
