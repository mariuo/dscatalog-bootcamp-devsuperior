import React, { useState, useEffect } from "react";
import { ScrollView, ActivityIndicator } from "react-native";
import { ProductCard, SearchInput } from "../components";
import { api } from "../services";

import { theme } from "../styles";



const Catalog: React.FC = () => {
    const [search, setSearch] = useState("");
    const [products, setProducts] = useState([]);
    const [loading, setLoading] = useState(false);



    async function fillProducts(){
        setLoading(true);
        const res = await api.get(`/products?page=0&linesPerPage=12&direction=ASC&orderBy=name`);
        //console.warn(res);
        setProducts(res.data.content);
        setLoading(false);
    }

    const data = 
        search.length > 0 ? 
            products.filter((product) => product.name.toLowerCase().includes(search.toLowerCase())) 
        : products;
    
    useEffect (()=>{
        fillProducts();
    },[]);

    return (
        <ScrollView contentContainerStyle={theme.scrollContainer}>
            <SearchInput
                placeHolder="Nome do produto"
                search={search}
                setSearch={setSearch}
            />
            { loading ? (<ActivityIndicator size="large" />) :
            (data.map((product) => (
                <ProductCard {...product} key={product.id} />

            )))}
        </ScrollView>
    )
}

export default Catalog;