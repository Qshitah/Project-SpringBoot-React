import React, { useState } from 'react';
import { useDispatch } from 'react-redux';
import { loginUser } from './AuthActions';

function Login() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');

  // Create functions to handle input changes
  const handleUsernameChange = (e) => {
    setUsername(e.target.value);
  };

  const handlePasswordChange = (e) => {
    setPassword(e.target.value);
  };

  const dispatch = useDispatch();

  const handleSubmit = (e) => {
    e.preventDefault();

    // Dispatch an action to check if the user exists
    dispatch(loginUser({ username, password }));
  };

  return (
    <div>
      <form onSubmit={handleSubmit}>
        <input
          type="text"
          placeholder="Username"
          value={username}
          onChange={handleUsernameChange}
        />
        <input
          type="password"
          placeholder="Password"
          value={password}
          onChange={handlePasswordChange}
        />
        <button type="submit">Login</button>
      </form>
    </div>
  );
}

export default Login;
