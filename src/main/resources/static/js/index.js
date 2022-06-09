function copyBtn(url) {
    navigator.clipboard.writeText(url)
    toastr.info("ClipBoard Copy It!")
}

function cardHtmlCreate(json) {
    const {convertUrl, documentTitle, originUrl, createDate} = json
    return `<div class="col-12 mb-3 border-start border-primary border-2">
                                <div class="card">
                                      <div class="card-body">
                                            <div class="d-flex justify-content-between">
                                                <p class="text-truncate mb-0 fs-5">
                                                    <a class="text-decoration-none align-middle" href="${originUrl}">${documentTitle}</a>
                                                </p>
                                                <p class="text-truncate mb-0 mt-0 border border-secondary rounded-1 fs-6 p-1">${createDate}</p>
                                            </div>
                                            <hr/>
                                            <div class="d-flex justify-content-between">
                                                <p class="text-danger mb-0">
                                                    ${convertUrl}<i class="fa-solid fa-copy copy-btn ms-3" onclick="copyBtn('${convertUrl}')"></i>
                                                </p>
                                                <a href="${convertUrl}-stat" class="">Show Analysis</a>
                                            </div>
                                      </div>
                                </div>
                        </div>`
}

function resultDraw() {
    let parse = JSON.parse(localStorage.getItem("site"));
    const divElem = document.querySelector("#result");
    divElem.innerHTML = parse.map(json => cardHtmlCreate(json)).join("")
}

window.addEventListener("load", function () {
    let type = new Typed(".typed", {
        attr: "placeholder",
        strings: ["http://www.example.com"],
        typeSpeed: 100,
        backSpeed: 40,
        loop: true
    })

    toastr.options = {
        "positionClass": "toast-bottom-right"
    }

    if (!localStorage.getItem("site")) {
        localStorage.setItem("site",JSON.stringify([]))
    }
    resultDraw()

    document.querySelector("#short-btn").addEventListener("click",() => {
        let urlElem = document.querySelector("#url-input");
        let originUrl = urlElem.value
        const urlRegex = /(http|https):\/\/(\w+:?\w*@)?(\S+)(:\d+)?(\/|\/([\w#!:.?+=&%@\-\/]))?/

        if (!urlRegex.test(originUrl)) {
            toastr.error("URL Check")
            return
        }
        $("#spinner").toggle()
        fetch("/convert",{
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({originUrl})
        }).then(res => {
            if (res.status === 200) {
                return res.json().then(body => {
                    return {body, success: true}
                })
            } else {
                return res.json().then(body => {
                    return {body, success:false}
                })
            }
        }).then(result => {
            if (!result.success) {
                throw new Error(result.body.message)
            }
            const parse = JSON.parse(localStorage.getItem("site"));
            parse.push(result.body)
            localStorage.setItem("site",JSON.stringify(parse))
            urlElem.value = ""
            toastr.success("success! convert complete")
            resultDraw()
            $("#spinner").toggle()
        }).catch(err => {
            $("#spinner").toggle()
            toastr.error(err)
        })
    })
})