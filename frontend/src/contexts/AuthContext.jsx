import React from 'react';
import { createContext, useContext, useState } from 'react';
import axios from 'axios'; // 🔄 백엔드 연동 시 사용

const AuthContext = createContext();

export function AuthProvider({ children }) {
  const [user, setUser] = useState(null);
  const [userList, setUserList] = useState([
    { username: 'testuser', password: '1234' } // 기본 테스트 유저
  ]);

  // ✅ 테스트용 회원가입
  const signup = (username, password) => {
    const exists = userList.find(u => u.username === username);
    if (exists) return false;
    setUserList(prev => [...prev, { username, password }]);
    return true;

    /*
    🔄 실제 백엔드 연동 시:
    try {
      await axios.post('/api/signup', { username, password });
      return true;
    } catch (err) {
      return false;
    }
    */
  };

  // ✅ 테스트용 로그인
  const login = (username, password) => {
    const found = userList.find(u => u.username === username && u.password === password);
    if (found) {
      setUser(found);
      return true;
    }
    return false;

    /*
    🔄 실제 백엔드 연동 시:
    try {
      const res = await axios.post('/api/login', { username, password });
      setUser(res.data.user); // 서버에서 받은 사용자 정보 저장
      return true;
    } catch (err) {
      return false;
    }
    */
  };

  const logout = () => setUser(null);

  return (
    <AuthContext.Provider value={{ user, login, signup, logout }}>
      {children}
    </AuthContext.Provider>
  );
}

export const useAuth = () => useContext(AuthContext);
