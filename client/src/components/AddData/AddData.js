import React, { useState, useEffect } from "react";
import { ImCheckmark } from "react-icons/im";
import { AiOutlineDelete } from "react-icons/ai";

import SearchField from "../SearchField/SearchField.js";
import "./AddData.scss";

//data: an array of all data objects that we want to select
//initialSelectedData: an array of selected data objects
//dataName:name that represents a data object
//onChange, we want to update selectedData in parent so it can send
//with form
const AddData = ({
  getDataName,
  isSelected,
  getDataId,
  labelName,
  inputPlaceholder,
  data,
  onSelectedChange,
  error,
  initialSelectedData,
}) => {
  const [allData, setAllData] = useState(data);

  const [suggestedData, setSuggestedData] = useState(allData.slice(0, 20));

  const [selectedData, setSelectedData] = useState(initialSelectedData);

  const [searchValue, setSearchValue] = useState("");

  useEffect(() => {
    setAllData(data);
    setSuggestedData(data.slice(0, 20));
  }, [data]);

  //when initialSelectedData changes,so from [] to [{}] update
  //selected data
  useEffect(() => {
    setSelectedData(initialSelectedData);
  }, [initialSelectedData]);

  //when selectedData change, update the data values the in parent createGroupForm
  //component
  useEffect(() => {
    onSelectedChange(selectedData);
  }, [selectedData]);

  //when search value changes, get filteredResults/suggested contacts
  useEffect(() => {
    onSearch();
  }, [searchValue]);

  //update the suggested contacts so we can render them
  const onSearch = () => {
    const suggestedData = allData.filter((dataObj) => {
      const dataName = getDataName(dataObj);

      return dataName.toLowerCase().includes(searchValue.toLowerCase());
    });

    setSuggestedData(suggestedData);
  };

  const isDataSelected = (dataObjId) => {
    return selectedData.some((selectedDataObj) => {
      return isSelected(selectedDataObj, dataObjId); //checks if a dataObj in selectedData has same id of obj we are trying to see is selected
    });
  };

  const removeDataFromSelected = (dataObjId) => {
    setSelectedData(
      selectedData.filter(
        (selectedDataObj) => !isSelected(selectedDataObj, dataObjId)
      ) //filter all selected objects that dont contain the id we are looking at
    );
  };

  const renderedSuggested = suggestedData.map((dataObj) => {
    return (
      <div className="AddData__result" key={getDataId(dataObj)}>
        <div className="AddData__pic-name">
          <input
            className="AddData__checkbox"
            type="checkbox"
            name="contact"
            id={getDataId(dataObj)}
            value={dataObj}
            checked={isDataSelected(getDataId(dataObj))}
            onChange={(e) => {
              if (e.target.checked) {
                setSelectedData([...selectedData, dataObj]);
              } else {
                setSelectedData(
                  selectedData.filter((selectedDataObj) => {
                    return !isSelected(selectedDataObj, getDataId(dataObj));
                  })
                );
              }
            }}
          />
          {getDataName(dataObj)}
        </div>
        <label
          className={`AddData__circle ${
            isDataSelected(getDataId(dataObj))
              ? "AddData__circle--selected"
              : null
          }`}
          htmlFor={getDataId(dataObj)}
        >
          <ImCheckmark className="AddData__icon" />
        </label>
      </div>
    );
  });

  const renderedSelected = selectedData.map((dataObj, index) => {
    return (
      <div className="AddData__selected" key={index}>
        <p className="AddData__selected-circle">
          {getDataName(dataObj).split("")[0]}
        </p>
        <p className="AddData__selected-name">{getDataName(dataObj)}</p>
        <button
          className="AddData__remove-selected-icon"
          onClick={(e) => {
            removeDataFromSelected(getDataId(dataObj));
            e.preventDefault();
          }}
        >
          <AiOutlineDelete />
        </button>
      </div>
    );
  });

  return (
    <div className="AddData">
      <p className="Form__label">{`${labelName}*`}</p>
      <SearchField
        placeholder={inputPlaceholder}
        onChange={setSearchValue}
        value={searchValue}
        onSearch={onSearch}
      />
      <span className="AddData__error">{error}</span>
      <div className="AddData__view">{renderedSelected}</div>
      <div className="AddData__suggestions">
        <p className="AddData__detail-text">
          {searchValue ? "Results" : "Suggested"}
        </p>
        {renderedSuggested}
      </div>
    </div>
  );
};

export default AddData;
