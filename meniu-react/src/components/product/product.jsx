import React from "react";

const Product = ({ data }) => {
  const { name, type, price, description } = data;

  return (
    <div className="card col-md-6 col-lg-4 product">
      <img src={`images/${name}.jpg`} className="card-img-top" alt={description?`${name} ${description}`:`${name}`} 
 onError={({ currentTarget }) => {
    currentTarget.onerror = null;
    currentTarget.src="https://dummyimage.com/640x640/ffffff/000000.png&text=not+found";
    currentTarget.style="max-height:15rem;max-width:15rem;object-fit:contain;"
  }} />
      <div className="card-body">
        <h5 className="card-title">
          {description ? `${name} ${description}` : name}
        </h5>
        <p className="card-text">
          {price} lei /{" "}
          {type === "bere" || type === "vin" ? ` sticla` : `50 ml`}
        </p>
        {/*
        <a href="#" className="btn btn-primary">
          add to cart
        </a>
        react comment
        */}
      </div>
    </div>
  );
};

export default Product;
