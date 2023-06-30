import validator from "validator";

//takes in formErrors
const validateField = (formValues, formErrors, fieldName) => {
  if (fieldName === "firstName") {
    if (!formValues.firstName) {
      formErrors["firstName"] = "Required";
    } else {
      delete formErrors["firstName"];
    }
    return formErrors;
  }

  if (fieldName === "lastName") {
    if (!formValues.lastName) {
      formErrors["lastName"] = "Required";
    } else {
      delete formErrors["lastName"];
    }
    return formErrors;
  }

  if (fieldName === "email") {
    if (!formValues.email) {
      formErrors["email"] = "Required";
    } else if (!validator.isEmail(formValues.email)) {
      formErrors["email"] = "Invalid email";
    } else {
      delete formErrors["email"];
    }
    return formErrors;
  }

  if (fieldName === "phone") {
    if (!formValues.phone) {
      formErrors["phone"] = "Required";
    } else if (!validator.isMobilePhone(formValues.phone)) {
      formErrors["phone"] = "Invalid phone number";
    } else {
      delete formErrors["phone"];
    }

    return formErrors;
  }
};

const validateAllFields = (formValues, updateErrorsFunction) => {
  const formErrors = {};

  Object.keys(formValues).forEach((field) => {
    validateField(formValues, formErrors, field, updateErrorsFunction);
  });

  updateErrorsFunction(formErrors);
};

export { validateField, validateAllFields };
