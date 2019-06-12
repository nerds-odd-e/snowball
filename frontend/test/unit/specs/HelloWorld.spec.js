import Vue from 'vue'
import HelloWorld from '@/components/HelloWorld'


describe('HelloWorld.vue', () => {
  it('should render correct contents', () => {
    const Constructor = Vue.extend(HelloWorld)
    const vm = new Constructor().$mount()
    expect(vm.$el.querySelector('#todos h1').textContent).toEqual('Todos List')
  })
})
