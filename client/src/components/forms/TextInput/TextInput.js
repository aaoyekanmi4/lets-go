import React from "react";

const TextInput = ({
  type,
  name,
  label,
  id,
  onChange,
  value,
  error,
  onBlur,
}) => {
  return (
    <div className="TextInput Form__group">
      <label className="Form__label" htmlFor={id}>
        {label}
      </label>
      <input
        className="Form__input"
        type={type}
        value={value}
        name={name}
        onChange={onChange}
        onBlur={onBlur}
        id={id}
      />
      <span className="Form__error">{error}</span>
    </div>
  );
};

export default TextInput;
