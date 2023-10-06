import axios from 'axios';

// Define action types
export const LOGIN_USER_SUCCESS = 'LOGIN_USER_SUCCESS';
export const LOGIN_USER_FAILURE = 'LOGIN_USER_FAILURE';

// Action creator for successful login
export const loginUserSuccess = (user) => ({
  type: LOGIN_USER_SUCCESS,
  payload: user,
});

// Action creator for login failure
export const loginUserFailure = (error) => ({
  type: LOGIN_USER_FAILURE,
  payload: error,
});

// Action to initiate the login process
export const loginUser = (userData) => {
  return async (dispatch) => {
    try {
      // Make an API request to your backend for authentication
      const response = await axios.post('http://localhost:8080/api/auth/login', userData);

      // Dispatch a success action with user data
      dispatch(loginUserSuccess(response.data));
      console.log(response.data);
    } catch (error) {
      // Dispatch a failure action with an error message
      dispatch(loginUserFailure('Invalid username or password.'));
      console.log('Invalid username or password.')
    }
  };
};
