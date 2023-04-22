/**/
export function getUserInfo(){
    return fetch('/api/users/info',{
        headers: {
            "Content-Type": "application/json",
        },
        credentials:"include",
    }).then(resp => resp.json())
}

export function login(form){
    let d = new FormData()
    d.set("username",form.username)
    d.set("password",form.password)
    return fetch('/api/users/login',{
        headers:{
           // 'Content-Type': 'application/x-ww-form-urlencoded',
        },
        method: 'POST',
        credentials: "include",
        body: d,
    }).then(resp => resp.json())
}