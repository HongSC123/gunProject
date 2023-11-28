<template>
	<div class="layout-topbar">
		<button class="p-link layout-menu-button" @click="onMenuToggle">
			<span class="pi pi-bars"></span>
		</button>
		
		<div class="layout-topbar-icons">
			<span style="font-size: 18px;">건강을 부탁해</span>
			
			<button class="p-link">
				<span class="layout-topbar-item-text">User</span>
				<span class="layout-topbar-icon pi pi-user"></span>
			</button>
		</div>
	</div>
</template>

<script>
export default {
    methods: {
        onMenuToggle(event) {
            this.$emit('menu-toggle', event);
        }
    }
}
</script>