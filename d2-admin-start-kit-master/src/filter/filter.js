


let optionKV = (list,key)=> {

    if(!list||!label){
        return "";
    }else{
        let result = "";
        for(let i=0;i<list.length;i++){
            if(key == list[i]["value"]){
                result = list[i]["label"];
            }
        }
        return result;
    }

};

export default{
    optionKV
}