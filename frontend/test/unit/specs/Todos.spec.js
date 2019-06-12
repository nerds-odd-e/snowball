import Vue from 'vue'
import Todos from '@/components/Todos'


describe('todos', () => {
  it('render h1', () => {
    const Constructor = Vue.extend(Todos)
    const vm = new Constructor().$mount()
    expect(vm.$el.querySelector('#todos h1').textContent).toEqual('Todos List')
  })
  it('render todo', () => {
  })
})
