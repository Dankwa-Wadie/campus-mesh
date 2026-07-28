document.addEventListener("DOMContentLoaded", async () => {
    const { peerId } = await getOrCreateBrowserKeypair();
    document.getElementById("user-id").innerText = `ID: ${peerId}`;

    const channelItems = document.querySelectorAll("#channel-list li");
    const activeChannelName = document.getElementById("active-channel-name");
    const chatInput = document.getElementById("chat-input");
    const sendBtn = document.getElementById("send-btn");

    channelItems.forEach(item => {
        item.addEventListener("click", () => {
            channelItems.forEach(i => i.classList.remove("active"));
            item.classList.add("active");
            const channel = item.getAttribute("data-channel");
            activeChannelName.innerText = `${item.innerText}`;

            if (channel.includes("announcements")) {
                chatInput.disabled = true;
                sendBtn.disabled = true;
                chatInput.placeholder = "Read-Only Official Channel (Verified Ed25519 Signatures)";
            } else {
                chatInput.disabled = false;
                sendBtn.disabled = false;
                chatInput.placeholder = "Type a message to nearby mesh peers...";
            }
        });
    });
});
