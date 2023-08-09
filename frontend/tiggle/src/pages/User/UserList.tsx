import React from "react";
import { Link } from "react-router-dom";

type UsersProps = {
  data: {
    id: string;
    name: string;
  }[];
};

function UserList({ data }: UsersProps) {
  return (
    <>
      <h2>User List</h2>
      <ul>
        {data.map(({ id, name }) => (
          <li key={id}>
            <Link to={`/users/${id}`}>{name}</Link>
          </li>
        ))}
      </ul>
    </>
  );
}

export default UserList;
