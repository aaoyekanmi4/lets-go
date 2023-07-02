import React from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";

import Home from "./views/pages/Home/Home.js";
import Login from "./views/pages/Login/Login.js";
import Register from "./views/pages/Register/Register.js";
import Contacts from "./views/pages/Contacts/Contacts.js";
import Groups from "./views/pages/Groups/Groups.js";
import SavedEvents from "./views/pages/SavedEvents/SavedEvents.js";
import UserAccount from "./views/pages/UserAccount/UserAccount.js";
import EventResults from "./views/pages/EventResults/EventResults.js";

import { loginUser } from "../src/actions/user.js";
loginUser({ username: "eric@dev10.com", password: "P@ssw0rd!" })();

const App = () => {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/" element={<Home />} />
        <Route path="/contacts/:userId" element={<Contacts />} />
        <Route path="/groups/:userId" element={<Groups />} />
        <Route path="/saved-events/:userId" element={<SavedEvents />} />
        <Route path="/account/:userId" element={<UserAccount />} />
        <Route path="/events" element={<EventResults />} />
      </Routes>
    </BrowserRouter>
  );
};

export default App;
