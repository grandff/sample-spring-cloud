// password 입력 체크
const checkPassword = () => {
    const passwordVal = document.querySelector("input[name=password]").value;
    const rePasswordVal = document.querySelector("input[name=repassword]").value;

    if(passwordVal !== rePasswordVal){
        alert("패스워드 입력값을 확인해주세요.")
        return false;
    }
    
    return true;
}