import validator from "validator";

import {
  validateFirstName,
  validateLastName,
  validateEmail,
  validatePhone,
} from "../validators.js";

//takes in a formErrors object that is constantly being modified
//so since being used to update formErrors state, pass in a brand new
//form Errors object
const validateField = (formValues, formErrors, fieldName) => {
  if (fieldName === "firstName") {
    return validateFirstName(formValues, formErrors);
  }

  if (fieldName === "lastName") {
    return validateLastName(formValues, formErrors);
  }

  if (fieldName === "email") {
    return validateEmail(formValues, formErrors);
  }

  if (fieldName === "phone") {
    return validatePhone(formValues, formErrors);
  }
};

export { validateField };
