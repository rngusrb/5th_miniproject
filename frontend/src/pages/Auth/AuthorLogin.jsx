import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../../contexts/AuthContext';
// import axios from 'axios'; // 🔄 실제 백엔드 연동 시 사용
import './AuthForm.css';

export default function AuthorLogin() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const { login } = useAuth();
  const navigate = useNavigate();

  const handleLogin = async () => {
    const success = login(username, password);
    if (success) {
      alert('로그인 성공!');
      navigate('/main/author');
    } else {
      alert('아이디 또는 비밀번호가 틀렸습니다.');
    }

    /*
    🔄 백엔드 연동 시:
    try {
      const res = await axios.post('/api/login/author', { username, password });
      setUser(res.data.user);
      navigate('/main/author');
    } catch (err) {
      alert('로그인 실패');
    }
    */
  };

  return (
    <div className="auth-container">
      <h2>작가 로그인</h2>
      <input
        placeholder="아이디"
        value={username}
        onChange={e => setUsername(e.target.value)}
      />
      <input
        type="password"
        placeholder="비밀번호"
        value={password}
        onChange={e => setPassword(e.target.value)}
      />
      <button onClick={handleLogin}>로그인</button>
      <p className="link-text">비밀번호를 잊으셨나요?</p>
    </div>
  );
}
