import React from "react";

import Header from "../../../components/Header/Header.js";
import CreateGroupForm from "../../../components/forms/CreateGroupForm/CreateGroupForm.js";
import "../../sharedStyles/formPage.scss";

const CreateGroup = () => {
  return (
    <div className="CreateGroup">
      <Header />
      <main className="form-page__main">
        <CreateGroupForm
          contacts={[
            "Elizah Jenkins",
            "Matt Owen",
            "Eric Baffour-Addo",
            "Seasoned Dunkin",
            "Ada Driver",
            "Rebeccah Soles",
          ]}
        />
      </main>
    </div>
  );
};

export default CreateGroup;
