import Vue from 'vue'
import Sample from '@/components/Sample'
import { mount } from '@vue/test-utils'

const nextTick = () => new Promise(res => process.nextTick(res));

describe('sample', () => {
  beforeEach(() => {
    fetch.resetMocks()
  })

  let component = (() => {
    fetch.mockResponseOnce([]);
    return mount(Sample)
  })

  it('show h1', () => {
    expect(component().find("#sample h1").text()).toEqual('Sample page!')
  })

  it('fetch response and re render', async () => {
    fetch.mockResponseOnce(JSON.stringify({msg:'hello!'}));
    const wrapper = mount(Sample)

    await nextTick()

    expect(fetch.mock.calls.length).toEqual(1)
    expect(fetch.mock.calls[0][0]).toEqual('/sample')
    expect(wrapper.vm.message).toEqual('hello!')
    expect(wrapper.find("#sample h2").text()).toEqual('hello!')
  })

})
