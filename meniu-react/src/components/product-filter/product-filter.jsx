import React, { useEffect, useState } from "react";
import Product from "../product";

const ProductFilter = ({ products }) => {
  const [filters, setFilters] = useState([]);
  const [selectedFilters, setSelectedFilters] = useState([]);

  useEffect(() => {
    setFilters([...new Set(products?.map((product) => product?.type))]);
  }, [products]);

  const handleClick = (filter) => {
    const checkSelectedFilterIndex = selectedFilters?.findIndex(
      (selectedFilter) => selectedFilter === filter
    );
    if (checkSelectedFilterIndex !== -1) {
      const removeFilterFromArray = selectedFilters?.filter(
        (selectedFilter) => selectedFilter !== filter
      );
      setSelectedFilters(removeFilterFromArray);
      return;
    }
    setSelectedFilters((previousState) => [...previousState, filter]);
  };

  return (
    <>
      <div className="container d-flex flex-wrap mb-2">
        <p className="d-flex align-items-center mb-0 me-2">Filters: </p>
        <div className="btn-group">
          {filters?.map((filter) => (
            <button
              type="button"
              className={`btn ${
                selectedFilters.includes(filter) ? "btn-primary" : "btn-dark"
              }`}
              onClick={() => handleClick(filter)}
            >
              {filter}
            </button>
          ))}
        </div>
      </div>
      <div className="container d-flex flex-wrap">
        {products?.map((product, idx) => {
          if (selectedFilters.length === 0) {
            return <Product data={product} key={idx} />;
          }
          if (
            selectedFilters.length > 0 &&
            selectedFilters.includes(product.type)
          ) {
            return <Product data={product} key={idx} />;
          }
        })}
      </div>
    </>
  );
};

export default ProductFilter;
