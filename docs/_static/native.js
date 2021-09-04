$(document).ready(function() {
    var url = "https://docs.microsoft.com/en-us/microsoft-365/cloud-storage-partner-program/mobile/";

    var grid = $("div.wy-grid-for-nav");
    if(grid) {
        grid.before(`<div id="docs-moved-banner" class="rst-content">
        <div class="admonition warning">
    <p class="admonition-title">Documentation has moved</p>
    <p>This documentation has moved to <a href="${url}">${url}</a></p>
    <p>This page will automatically redirect to the new URL after three seconds.</a></p>
    </div>
    </div>`);
    }

    window.setTimeout(function () {
        location.href = url;
    }, 3000);
});
