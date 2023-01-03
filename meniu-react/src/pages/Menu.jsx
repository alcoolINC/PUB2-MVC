import React, { useEffect, useState } from "react";
import request from "../../../PUB2-MVC/jSonProduse.json";
import ProductFilter from "../components/product-filter";

const Menu = () => {
  const [data, setData] = useState([]);

  useEffect(() => {
    setData(request);
  }, []);

  return (
    <div className="d-flex flex-column align-items-center">
      <h1>Meniu Bar</h1>
      <p className="p-3">
        Lorem ipsum dolor sit amet consectetur adipisicing elit. Facere sequi
        excepturi, eligendi vero ab vitae laudantium earum non autem quam velit
        provident possimus doloremque aliquam, omnis assumenda obcaecati
        incidunt commodi?
      </p>
      <ProductFilter products={data?.products} />
    </div>
  );
};

export default Menu;
