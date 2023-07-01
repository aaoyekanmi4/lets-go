import validator from "validator";

const validateName = (formValues, formErrors) => {
  if (!formValues.name) {
    formErrors["name"] = "Required";
  } else {
    delete formErrors["name"];
  }

  return formErrors;
};

const validateFirstName = (formValues, formErrors) => {
  if (!formValues.firstName) {
    formErrors["firstName"] = "Required";
  } else {
    delete formErrors["firstName"];
  }

  return formErrors;
};

const validateLastName = (formValues, formErrors) => {
  if (!formValues.lastName) {
    formErrors["lastName"] = "Required";
  } else {
    delete formErrors["lastName"];
  }

  return formErrors;
};

const validateEmail = (formValues, formErrors) => {
  if (!formValues.email) {
    formErrors["email"] = "Required";
  } else if (!validator.isEmail(formValues.email)) {
    formErrors["email"] = "Invalid email";
  } else {
    delete formErrors["email"];
  }

  return formErrors;
};

const validatePhone = (formValues, formErrors) => {
  if (!formValues.phone) {
    formErrors["phone"] = "Required";
  } else if (!validator.isMobilePhone(formValues.phone)) {
    formErrors["phone"] = "Invalid phone number";
  } else {
    delete formErrors["phone"];
  }

  return formErrors;
};

const validateContacts = (formValues, formErrors) => {
  if (formValues.contacts.length !== 2) {
    formErrors["contacts"] = "Must add at least 2 contacts";
  } else {
    delete formErrors["contacts"];
  }

  return formErrors;
};

const validateUsername = (formValues, formErrors) => {
  if (!formValues.username) {
    formErrors.username = "Required";
  } else if (formValues.username.length > 50) {
    formErrors.username = "Username must be less than 50 character";
  } else {
    delete formErrors.username;
  }

  return formErrors;
};

const validatePassword = (formValues, formErrors) => {
  if (!formValues.password) {
    formErrors.password = "Required";
  } else if (formValues.password.length < 8) {
    formErrors.password = "Password must be at least 8 characters";
  } else if (!containsDigit(formValues.password)) {
    formErrors.password = "Password must contain a digit";
  } else if (!containsLetter(formValues.password)) {
    formErrors.password = "Password must contain a letter";
  } else if (!containsSpecialCharacter(formValues.password)) {
    formErrors.password = "Password must contain a non-digit/non-letter";
  } else {
    delete formErrors.password;
  }

  return formErrors;
};

const containsDigit = (string) => {
  const regex = /[0-9]/;
  return regex.test(string);
};

const containsLetter = (string) => {
  const regex = /[a-zA-Z]/;
  return regex.test(string);
};

const containsSpecialCharacter = (string) => {
  const regex = /[^a-zA-Z0-9]/g;
  return regex.test(string);
};

const validateAllFields = (
  validateFieldFunction,
  formValues,
  updateErrorsFunction
) => {
  const formErrors = {};

  Object.keys(formValues).forEach((field) => {
    validateFieldFunction(formValues, formErrors, field);
  });

  updateErrorsFunction(formErrors);
};

export {
  validateName,
  validateFirstName,
  validateLastName,
  validateEmail,
  validatePhone,
  validateUsername,
  validatePassword,
  validateContacts,
  validateAllFields,
};
