<template>
	<div class="p-grid p-fluid">
		<div class="p-col-12 p-md-6">
			<div class="card">
				<h5><b>성별정보</b></h5>
				<div class="p-grid">
					<div class="p-col-12 p-md-4">
						<div class="p-field-radiobutton">
							<RadioButton id="option1" name="option" value="female" v-model="radioValue" />
							<label for="option1">여성</label>
						</div>
					</div>
					<div class="p-col-12 p-md-4">
						<div class="p-field-radiobutton">
							<RadioButton id="option2" name="option" value="male" v-model="radioValue" />
							<label for="option2">남성</label>
						</div>
					</div>
				</div>
			</div>
		</div>

		<div class="p-col-12">
			<div class="card">
				<h5><b>신체정보</b></h5>
				<div class="p-grid p-fluid">
					<div class="p-col-12 p-md-6">
						<div class="p-inputgroup">
						
							<InputText placeholder="키를 입력하세요"/>
							<span class="p-inputgroup-addon">cm</span>
						</div>
					</div>

					<div class="p-col-12 p-md-6">
						<div class="p-inputgroup">
							
							<InputText placeholder="몸무게를 입력하세요"/>
							<span class="p-inputgroup-addon">kg</span>
							
						</div>
					</div>

					<div class="p-col-12 p-md-6">
						<div class="p-inputgroup">
							
							<InputText placeholder="나이를 입력하세요"/>
							<span class="p-inputgroup-addon">세</span>
						</div>
					</div>

					<div class="p-col-12 p-md-6">
						<div class="p-inputgroup">
							
							<Button label="계산하기"></Button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</template>
<script>
	import CountryService from '../service/CountryService';
	export default {
		data() {
			return {
				floatValue: null,
				autoValue: null,
				selectedAutoValue: null,
				autoFilteredValue: [],
				calendarValue: null,
				inputNumberValue: null,
				chipsValue: null,
				sliderValue: 50,
				ratingValue: null,
				colorValue: '1976D2',
				radioValue: null,
				checkboxValue: [],
				switchValue: false,
				listboxValues: [
					{name: 'New York', code: 'NY'},
					{name: 'Rome', code: 'RM'},
					{name: 'London', code: 'LDN'},
					{name: 'Istanbul', code: 'IST'},
					{name: 'Paris', code: 'PRS'}
				],
				listboxValue: null,
				dropdownValues: [
					{name: 'New York', code: 'NY'},
					{name: 'Rome', code: 'RM'},
					{name: 'London', code: 'LDN'},
					{name: 'Istanbul', code: 'IST'},
					{name: 'Paris', code: 'PRS'}
				],
				dropdownValue: null,
				multiselectValue: null,
				multiselectValues: [
					{name: 'Australia', code: 'AU'},
					{name: 'Brazil', code: 'BR'},
					{name: 'China', code: 'CN'},
					{name: 'Egypt', code: 'EG'},
					{name: 'France', code: 'FR'},
					{name: 'Germany', code: 'DE'},
					{name: 'India', code: 'IN'},
					{name: 'Japan', code: 'JP'},
					{name: 'Spain', code: 'ES'},
					{name: 'United States', code: 'US'}
				],
				toggleValue: false,
				selectButtonValues1: [
					{name: 'Option 1', code: 'O1'},
					{name: 'Option 2', code: 'O2'},
					{name: 'Option 3', code: 'O3'},
				],
				selectButtonValue1: null,
				selectButtonValues2: [
					{name: 'Option 1', code: 'O1'},
					{name: 'Option 2', code: 'O2'},
					{name: 'Option 3', code: 'O3'},
				],
				selectButtonValue2: null,
				inputGroupValue: false,
				knobValue: 20
			}
		},
		countryService: null,
		created() {
			this.countryService = new CountryService();
		},
		mounted() {
			this.countryService.getCountries().then(data => this.autoValue = data);
		},
		methods: {
			searchCountry(event) {
				setTimeout(() => {
					if (!event.query.trim().length) {
						this.autoFilteredValue = [...this.autoValue];
					}
					else {
						this.autoFilteredValue = this.autoValue.filter((country) => {
							return country.name.toLowerCase().startsWith(event.query.toLowerCase());
						});
					}
				}, 250);
			}
		}
	}
</script>

<style scoped lang="scss">
	::v-deep(.p-multiselect) {
		min-width: 15rem;
	}
	.multiselect-custom-virtual-scroll .p-multiselect {
		min-width: 20rem;
	}
	::v-deep(.multiselect-custom .p-multiselect-label) {
		padding-top: .25rem;
		padding-bottom: .25rem;
	}
	.multiselect-custom .country-item.country-item-value {
		padding: .25rem .5rem;
		border-radius: 3px;
		display: inline-flex;
		margin-right: .5rem;
		background-color: var(--success-color);
		color: var(--success-color-text);
	}
	.multiselect-custom .country-item.country-item-value span.flag {
		width: 17px;
	}
	.country-item {
		display: flex;
		align-items: center;
	}
	.country-item span.flag {
		width: 18px;
		height: 12px;
		margin-right: .5rem;
	}
	.multiselect-custom .country-placeholder {
		padding: 0.25rem;
	}
</style>
