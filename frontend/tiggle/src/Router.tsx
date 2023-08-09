import { Routes, Route } from "react-router-dom";

import Main from "./pages/Main";
import Sample from "./pages/Sample";
import Users from "./pages/User";
import UserDetail from "./pages/User/UserDetail";

const usersData = [
  {
    id: "1",
    name: "user#1",
  },
  {
    id: "2",
    name: "user#2",
  },
  {
    id: "3",
    name: "user#3",
  },
];

export default function Router() {
  return (
    <Routes>
      <Route path="/" element={<Main />} />
      <Route path="/sample" element={<Sample />} />
      <Route path="/users" element={<Users data={usersData} />} />
      <Route path="/users/:id" element={<UserDetail />} />
    </Routes>
  );
}
