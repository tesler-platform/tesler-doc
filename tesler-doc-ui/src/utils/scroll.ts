
export function resetContentScroll() {
    const contentBySelector = document.getElementsByClassName('ant-layout')
    if (contentBySelector.length > 1) {
        contentBySelector.item(1).scrollTo(0, 0)
    } else {
        console.warn('Content panel was not found, it should be a second item caught by `.ant-layout` selector')
    }
}
