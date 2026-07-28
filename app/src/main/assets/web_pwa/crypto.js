// Web Crypto API Keypair Management for Safari & Chrome PWA
async function getOrCreateBrowserKeypair() {
    let peerId = localStorage.getItem("campusmesh_peer_id");
    if (!peerId) {
        const randomBytes = new Uint8Array(3);
        crypto.getRandomValues(randomBytes);
        const hex = Array.from(randomBytes).map(b => b.toString(16).padStart(2, '0')).join('');
        peerId = `web-${hex}`;
        localStorage.setItem("campusmesh_peer_id", peerId);
    }
    return { peerId };
}
