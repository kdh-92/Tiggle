import React from "react";

import UserList from "./UserList";

type UsersProps = {
  data: {
    id: string;
    name: string;
  }[];
};

function Users({ data }: UsersProps) {
  return (
    <>
      <h1>Users</h1>
      <UserList data={data} />
    </>
  );
}

export default Users;
